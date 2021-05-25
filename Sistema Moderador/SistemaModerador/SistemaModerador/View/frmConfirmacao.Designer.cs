
namespace SistemaModerador.View
{
    partial class frmConfirmacao
    {
        /// <summary>
        /// Required designer variable.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// Clean up any resources being used.
        /// </summary>
        /// <param name="disposing">true if managed resources should be disposed; otherwise, false.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form Designer generated code

        /// <summary>
        /// Required method for Designer support - do not modify
        /// the contents of this method with the code editor.
        /// </summary>
        private void InitializeComponent()
        {
            this.components = new System.ComponentModel.Container();
            this.txtCodigo = new System.Windows.Forms.TextBox();
            this.label3 = new System.Windows.Forms.Label();
            this.txtConfirmarCodigo = new System.Windows.Forms.Button();
            this.lbTempo = new System.Windows.Forms.Label();
            this.timer = new System.Windows.Forms.Timer(this.components);
            this.SuspendLayout();
            // 
            // txtCodigo
            // 
            this.txtCodigo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            this.txtCodigo.Font = new System.Drawing.Font("Century", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.txtCodigo.ForeColor = System.Drawing.Color.White;
            this.txtCodigo.Location = new System.Drawing.Point(12, 31);
            this.txtCodigo.Name = "txtCodigo";
            this.txtCodigo.Size = new System.Drawing.Size(279, 27);
            this.txtCodigo.TabIndex = 15;
            this.txtCodigo.TextAlign = System.Windows.Forms.HorizontalAlignment.Center;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label3.ForeColor = System.Drawing.Color.White;
            this.label3.Location = new System.Drawing.Point(13, 9);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(275, 19);
            this.label3.TabIndex = 14;
            this.label3.Text = "Digite o código enviado para seu e-mail";
            // 
            // txtConfirmarCodigo
            // 
            this.txtConfirmarCodigo.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtConfirmarCodigo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(8)))), ((int)(((byte)(15)))), ((int)(((byte)(0)))));
            this.txtConfirmarCodigo.Cursor = System.Windows.Forms.Cursors.Hand;
            this.txtConfirmarCodigo.FlatAppearance.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.txtConfirmarCodigo.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.txtConfirmarCodigo.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.txtConfirmarCodigo.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.txtConfirmarCodigo.Location = new System.Drawing.Point(12, 64);
            this.txtConfirmarCodigo.Name = "txtConfirmarCodigo";
            this.txtConfirmarCodigo.Size = new System.Drawing.Size(279, 30);
            this.txtConfirmarCodigo.TabIndex = 32;
            this.txtConfirmarCodigo.Text = "Confirmar código";
            this.txtConfirmarCodigo.UseVisualStyleBackColor = false;
            this.txtConfirmarCodigo.Click += new System.EventHandler(this.txtConfirmarCodigo_Click);
            // 
            // lbTempo
            // 
            this.lbTempo.AutoSize = true;
            this.lbTempo.Font = new System.Drawing.Font("Candara", 11.25F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.lbTempo.ForeColor = System.Drawing.Color.White;
            this.lbTempo.Location = new System.Drawing.Point(12, 97);
            this.lbTempo.Name = "lbTempo";
            this.lbTempo.Size = new System.Drawing.Size(117, 18);
            this.lbTempo.TabIndex = 33;
            this.lbTempo.Text = "Enviando e-mail...";
            // 
            // timer
            // 
            this.timer.Enabled = true;
            this.timer.Interval = 1000;
            this.timer.Tick += new System.EventHandler(this.timer_Tick);
            // 
            // frmConfirmacao
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            this.ClientSize = new System.Drawing.Size(303, 127);
            this.Controls.Add(this.lbTempo);
            this.Controls.Add(this.txtConfirmarCodigo);
            this.Controls.Add(this.txtCodigo);
            this.Controls.Add(this.label3);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedSingle;
            this.MaximizeBox = false;
            this.MinimizeBox = false;
            this.Name = "frmConfirmacao";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "Confirmar identidade";
            this.Shown += new System.EventHandler(this.frmConfirmacao_Shown);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.TextBox txtCodigo;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.Button txtConfirmarCodigo;
        private System.Windows.Forms.Label lbTempo;
        private System.Windows.Forms.Timer timer;
    }
}