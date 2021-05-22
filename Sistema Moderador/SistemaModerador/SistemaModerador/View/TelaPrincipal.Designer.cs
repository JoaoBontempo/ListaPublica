﻿
namespace SistemaModerador
{
    partial class TelaPrincipal
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
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle1 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle2 = new System.Windows.Forms.DataGridViewCellStyle();
            System.Windows.Forms.DataGridViewCellStyle dataGridViewCellStyle3 = new System.Windows.Forms.DataGridViewCellStyle();
            this.dgvDenuncias = new System.Windows.Forms.DataGridView();
            this.clnId = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.clnTipo = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.clnLocal = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.clnDescricao = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.clnDenunciador = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.clnDenunciado = new System.Windows.Forms.DataGridViewTextBoxColumn();
            this.pbxLupa = new System.Windows.Forms.PictureBox();
            this.txtPesquisar = new System.Windows.Forms.TextBox();
            this.label1 = new System.Windows.Forms.Label();
            this.rbComentario = new System.Windows.Forms.RadioButton();
            this.rbTelefone = new System.Windows.Forms.RadioButton();
            this.label2 = new System.Windows.Forms.Label();
            this.txtTipo = new System.Windows.Forms.TextBox();
            this.panel1 = new System.Windows.Forms.Panel();
            this.rbQualquer = new System.Windows.Forms.RadioButton();
            this.btnFiltrar = new System.Windows.Forms.Button();
            this.txtID = new System.Windows.Forms.TextBox();
            this.panel2 = new System.Windows.Forms.Panel();
            this.label3 = new System.Windows.Forms.Label();
            this.txtDescricao = new System.Windows.Forms.TextBox();
            this.label4 = new System.Windows.Forms.Label();
            this.btnAtualizar = new System.Windows.Forms.Button();
            this.groupBox1 = new System.Windows.Forms.GroupBox();
            this.rbQualquerStatus = new System.Windows.Forms.RadioButton();
            this.rbFechado = new System.Windows.Forms.RadioButton();
            this.rbAberto = new System.Windows.Forms.RadioButton();
            ((System.ComponentModel.ISupportInitialize)(this.dgvDenuncias)).BeginInit();
            ((System.ComponentModel.ISupportInitialize)(this.pbxLupa)).BeginInit();
            this.groupBox1.SuspendLayout();
            this.SuspendLayout();
            // 
            // dgvDenuncias
            // 
            dataGridViewCellStyle1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            dataGridViewCellStyle1.Font = new System.Drawing.Font("Candara", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            dataGridViewCellStyle1.ForeColor = System.Drawing.Color.White;
            dataGridViewCellStyle1.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            dataGridViewCellStyle1.SelectionForeColor = System.Drawing.Color.Black;
            this.dgvDenuncias.AlternatingRowsDefaultCellStyle = dataGridViewCellStyle1;
            this.dgvDenuncias.Anchor = ((System.Windows.Forms.AnchorStyles)((((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Bottom) 
            | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.dgvDenuncias.AutoSizeColumnsMode = System.Windows.Forms.DataGridViewAutoSizeColumnsMode.Fill;
            this.dgvDenuncias.BackgroundColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            this.dgvDenuncias.BorderStyle = System.Windows.Forms.BorderStyle.Fixed3D;
            this.dgvDenuncias.ColumnHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.Single;
            dataGridViewCellStyle2.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleCenter;
            dataGridViewCellStyle2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            dataGridViewCellStyle2.Font = new System.Drawing.Font("Candara", 11.25F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            dataGridViewCellStyle2.ForeColor = System.Drawing.Color.Black;
            dataGridViewCellStyle2.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            dataGridViewCellStyle2.SelectionForeColor = System.Drawing.Color.Black;
            dataGridViewCellStyle2.WrapMode = System.Windows.Forms.DataGridViewTriState.True;
            this.dgvDenuncias.ColumnHeadersDefaultCellStyle = dataGridViewCellStyle2;
            this.dgvDenuncias.ColumnHeadersHeightSizeMode = System.Windows.Forms.DataGridViewColumnHeadersHeightSizeMode.AutoSize;
            this.dgvDenuncias.Columns.AddRange(new System.Windows.Forms.DataGridViewColumn[] {
            this.clnId,
            this.clnTipo,
            this.clnLocal,
            this.clnDescricao,
            this.clnDenunciador,
            this.clnDenunciado});
            dataGridViewCellStyle3.Alignment = System.Windows.Forms.DataGridViewContentAlignment.MiddleLeft;
            dataGridViewCellStyle3.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(22)))), ((int)(((byte)(34)))), ((int)(((byte)(41)))));
            dataGridViewCellStyle3.Font = new System.Drawing.Font("Segoe UI", 9F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            dataGridViewCellStyle3.ForeColor = System.Drawing.Color.White;
            dataGridViewCellStyle3.SelectionBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            dataGridViewCellStyle3.SelectionForeColor = System.Drawing.Color.Black;
            dataGridViewCellStyle3.WrapMode = System.Windows.Forms.DataGridViewTriState.False;
            this.dgvDenuncias.DefaultCellStyle = dataGridViewCellStyle3;
            this.dgvDenuncias.EnableHeadersVisualStyles = false;
            this.dgvDenuncias.GridColor = System.Drawing.Color.Black;
            this.dgvDenuncias.Location = new System.Drawing.Point(12, 225);
            this.dgvDenuncias.MultiSelect = false;
            this.dgvDenuncias.Name = "dgvDenuncias";
            this.dgvDenuncias.ReadOnly = true;
            this.dgvDenuncias.RowHeadersBorderStyle = System.Windows.Forms.DataGridViewHeaderBorderStyle.None;
            this.dgvDenuncias.RowHeadersVisible = false;
            this.dgvDenuncias.RowTemplate.Height = 25;
            this.dgvDenuncias.ScrollBars = System.Windows.Forms.ScrollBars.Vertical;
            this.dgvDenuncias.SelectionMode = System.Windows.Forms.DataGridViewSelectionMode.FullRowSelect;
            this.dgvDenuncias.Size = new System.Drawing.Size(1038, 202);
            this.dgvDenuncias.TabIndex = 0;
            // 
            // clnId
            // 
            this.clnId.HeaderText = "ID";
            this.clnId.Name = "clnId";
            this.clnId.ReadOnly = true;
            this.clnId.Visible = false;
            // 
            // clnTipo
            // 
            this.clnTipo.HeaderText = "Tipo";
            this.clnTipo.Name = "clnTipo";
            this.clnTipo.ReadOnly = true;
            // 
            // clnLocal
            // 
            this.clnLocal.HeaderText = "Local";
            this.clnLocal.Name = "clnLocal";
            this.clnLocal.ReadOnly = true;
            // 
            // clnDescricao
            // 
            this.clnDescricao.HeaderText = "Descrição";
            this.clnDescricao.Name = "clnDescricao";
            this.clnDescricao.ReadOnly = true;
            // 
            // clnDenunciador
            // 
            this.clnDenunciador.HeaderText = "Denunciador";
            this.clnDenunciador.Name = "clnDenunciador";
            this.clnDenunciador.ReadOnly = true;
            // 
            // clnDenunciado
            // 
            this.clnDenunciado.HeaderText = "Denunciado";
            this.clnDenunciado.Name = "clnDenunciado";
            this.clnDenunciado.ReadOnly = true;
            // 
            // pbxLupa
            // 
            this.pbxLupa.Image = global::SistemaModerador.Properties.Resources.lupa;
            this.pbxLupa.Location = new System.Drawing.Point(12, 192);
            this.pbxLupa.Name = "pbxLupa";
            this.pbxLupa.Size = new System.Drawing.Size(30, 27);
            this.pbxLupa.SizeMode = System.Windows.Forms.PictureBoxSizeMode.Zoom;
            this.pbxLupa.TabIndex = 1;
            this.pbxLupa.TabStop = false;
            // 
            // txtPesquisar
            // 
            this.txtPesquisar.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtPesquisar.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            this.txtPesquisar.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.txtPesquisar.ForeColor = System.Drawing.Color.White;
            this.txtPesquisar.Location = new System.Drawing.Point(48, 192);
            this.txtPesquisar.Name = "txtPesquisar";
            this.txtPesquisar.Size = new System.Drawing.Size(1002, 27);
            this.txtPesquisar.TabIndex = 2;
            // 
            // label1
            // 
            this.label1.AutoSize = true;
            this.label1.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label1.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.label1.Location = new System.Drawing.Point(12, 9);
            this.label1.Name = "label1";
            this.label1.Size = new System.Drawing.Size(124, 19);
            this.label1.TabIndex = 4;
            this.label1.Text = "Filtrar denúncias";
            // 
            // rbComentario
            // 
            this.rbComentario.AutoSize = true;
            this.rbComentario.FlatAppearance.BorderColor = System.Drawing.Color.White;
            this.rbComentario.FlatAppearance.BorderSize = 0;
            this.rbComentario.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.rbComentario.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Red;
            this.rbComentario.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Yellow;
            this.rbComentario.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.rbComentario.ForeColor = System.Drawing.Color.White;
            this.rbComentario.Location = new System.Drawing.Point(12, 77);
            this.rbComentario.Name = "rbComentario";
            this.rbComentario.Size = new System.Drawing.Size(108, 23);
            this.rbComentario.TabIndex = 5;
            this.rbComentario.Text = "Comentário";
            this.rbComentario.UseVisualStyleBackColor = true;
            // 
            // rbTelefone
            // 
            this.rbTelefone.AutoSize = true;
            this.rbTelefone.FlatAppearance.BorderColor = System.Drawing.Color.White;
            this.rbTelefone.FlatAppearance.BorderSize = 0;
            this.rbTelefone.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.rbTelefone.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Red;
            this.rbTelefone.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Yellow;
            this.rbTelefone.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.rbTelefone.ForeColor = System.Drawing.Color.White;
            this.rbTelefone.Location = new System.Drawing.Point(12, 103);
            this.rbTelefone.Name = "rbTelefone";
            this.rbTelefone.Size = new System.Drawing.Size(86, 23);
            this.rbTelefone.TabIndex = 6;
            this.rbTelefone.Text = "Telefone";
            this.rbTelefone.UseVisualStyleBackColor = true;
            // 
            // label2
            // 
            this.label2.AutoSize = true;
            this.label2.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label2.ForeColor = System.Drawing.Color.White;
            this.label2.Location = new System.Drawing.Point(265, 33);
            this.label2.Name = "label2";
            this.label2.Size = new System.Drawing.Size(39, 19);
            this.label2.TabIndex = 7;
            this.label2.Text = "Tipo";
            // 
            // txtTipo
            // 
            this.txtTipo.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtTipo.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            this.txtTipo.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.txtTipo.ForeColor = System.Drawing.Color.White;
            this.txtTipo.Location = new System.Drawing.Point(265, 55);
            this.txtTipo.Name = "txtTipo";
            this.txtTipo.Size = new System.Drawing.Size(474, 27);
            this.txtTipo.TabIndex = 8;
            // 
            // panel1
            // 
            this.panel1.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(22)))), ((int)(((byte)(34)))), ((int)(((byte)(41)))));
            this.panel1.Location = new System.Drawing.Point(120, 51);
            this.panel1.Name = "panel1";
            this.panel1.Size = new System.Drawing.Size(2, 80);
            this.panel1.TabIndex = 9;
            // 
            // rbQualquer
            // 
            this.rbQualquer.AutoSize = true;
            this.rbQualquer.Checked = true;
            this.rbQualquer.FlatAppearance.BorderColor = System.Drawing.Color.White;
            this.rbQualquer.FlatAppearance.BorderSize = 0;
            this.rbQualquer.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.rbQualquer.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Red;
            this.rbQualquer.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Yellow;
            this.rbQualquer.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.rbQualquer.ForeColor = System.Drawing.Color.White;
            this.rbQualquer.Location = new System.Drawing.Point(12, 52);
            this.rbQualquer.Name = "rbQualquer";
            this.rbQualquer.Size = new System.Drawing.Size(91, 23);
            this.rbQualquer.TabIndex = 10;
            this.rbQualquer.TabStop = true;
            this.rbQualquer.Text = "Qualquer";
            this.rbQualquer.UseVisualStyleBackColor = true;
            // 
            // btnFiltrar
            // 
            this.btnFiltrar.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.btnFiltrar.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(8)))), ((int)(((byte)(15)))), ((int)(((byte)(0)))));
            this.btnFiltrar.Cursor = System.Windows.Forms.Cursors.Hand;
            this.btnFiltrar.FlatAppearance.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.btnFiltrar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnFiltrar.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.btnFiltrar.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.btnFiltrar.Location = new System.Drawing.Point(12, 143);
            this.btnFiltrar.Name = "btnFiltrar";
            this.btnFiltrar.Size = new System.Drawing.Size(1038, 30);
            this.btnFiltrar.TabIndex = 11;
            this.btnFiltrar.Text = "Filtrar informações";
            this.btnFiltrar.UseVisualStyleBackColor = false;
            // 
            // txtID
            // 
            this.txtID.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            this.txtID.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.txtID.ForeColor = System.Drawing.Color.White;
            this.txtID.Location = new System.Drawing.Point(131, 55);
            this.txtID.Name = "txtID";
            this.txtID.Size = new System.Drawing.Size(117, 27);
            this.txtID.TabIndex = 13;
            // 
            // panel2
            // 
            this.panel2.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.panel2.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(22)))), ((int)(((byte)(34)))), ((int)(((byte)(41)))));
            this.panel2.Location = new System.Drawing.Point(12, 182);
            this.panel2.Name = "panel2";
            this.panel2.Size = new System.Drawing.Size(1038, 2);
            this.panel2.TabIndex = 10;
            // 
            // label3
            // 
            this.label3.AutoSize = true;
            this.label3.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label3.ForeColor = System.Drawing.Color.White;
            this.label3.Location = new System.Drawing.Point(131, 33);
            this.label3.Name = "label3";
            this.label3.Size = new System.Drawing.Size(97, 19);
            this.label3.TabIndex = 12;
            this.label3.Text = "ID - Denúncia";
            // 
            // txtDescricao
            // 
            this.txtDescricao.Anchor = ((System.Windows.Forms.AnchorStyles)(((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Left) 
            | System.Windows.Forms.AnchorStyles.Right)));
            this.txtDescricao.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            this.txtDescricao.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Regular, System.Drawing.GraphicsUnit.Point);
            this.txtDescricao.ForeColor = System.Drawing.Color.White;
            this.txtDescricao.Location = new System.Drawing.Point(131, 107);
            this.txtDescricao.Name = "txtDescricao";
            this.txtDescricao.Size = new System.Drawing.Size(919, 27);
            this.txtDescricao.TabIndex = 15;
            // 
            // label4
            // 
            this.label4.AutoSize = true;
            this.label4.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.label4.ForeColor = System.Drawing.Color.White;
            this.label4.Location = new System.Drawing.Point(131, 85);
            this.label4.Name = "label4";
            this.label4.Size = new System.Drawing.Size(75, 19);
            this.label4.TabIndex = 14;
            this.label4.Text = "Descrição";
            // 
            // btnAtualizar
            // 
            this.btnAtualizar.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Bottom | System.Windows.Forms.AnchorStyles.Left)));
            this.btnAtualizar.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(8)))), ((int)(((byte)(15)))), ((int)(((byte)(0)))));
            this.btnAtualizar.Cursor = System.Windows.Forms.Cursors.Hand;
            this.btnAtualizar.FlatAppearance.BorderColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.btnAtualizar.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.btnAtualizar.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.btnAtualizar.ForeColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.btnAtualizar.Location = new System.Drawing.Point(12, 433);
            this.btnAtualizar.Name = "btnAtualizar";
            this.btnAtualizar.Size = new System.Drawing.Size(185, 30);
            this.btnAtualizar.TabIndex = 20;
            this.btnAtualizar.Text = "Ver últimas denúncias";
            this.btnAtualizar.UseVisualStyleBackColor = false;
            // 
            // groupBox1
            // 
            this.groupBox1.Anchor = ((System.Windows.Forms.AnchorStyles)((System.Windows.Forms.AnchorStyles.Top | System.Windows.Forms.AnchorStyles.Right)));
            this.groupBox1.Controls.Add(this.rbQualquerStatus);
            this.groupBox1.Controls.Add(this.rbFechado);
            this.groupBox1.Controls.Add(this.rbAberto);
            this.groupBox1.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.groupBox1.ForeColor = System.Drawing.Color.White;
            this.groupBox1.Location = new System.Drawing.Point(745, 33);
            this.groupBox1.Name = "groupBox1";
            this.groupBox1.Size = new System.Drawing.Size(305, 49);
            this.groupBox1.TabIndex = 21;
            this.groupBox1.TabStop = false;
            this.groupBox1.Text = "Status";
            // 
            // rbQualquerStatus
            // 
            this.rbQualquerStatus.AutoSize = true;
            this.rbQualquerStatus.Checked = true;
            this.rbQualquerStatus.FlatAppearance.BorderColor = System.Drawing.Color.Blue;
            this.rbQualquerStatus.FlatAppearance.BorderSize = 0;
            this.rbQualquerStatus.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.rbQualquerStatus.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Red;
            this.rbQualquerStatus.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Yellow;
            this.rbQualquerStatus.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.rbQualquerStatus.ForeColor = System.Drawing.Color.White;
            this.rbQualquerStatus.Location = new System.Drawing.Point(6, 22);
            this.rbQualquerStatus.Name = "rbQualquerStatus";
            this.rbQualquerStatus.Size = new System.Drawing.Size(91, 23);
            this.rbQualquerStatus.TabIndex = 24;
            this.rbQualquerStatus.TabStop = true;
            this.rbQualquerStatus.Text = "Qualquer";
            this.rbQualquerStatus.UseVisualStyleBackColor = true;
            // 
            // rbFechado
            // 
            this.rbFechado.AutoSize = true;
            this.rbFechado.FlatAppearance.BorderColor = System.Drawing.Color.White;
            this.rbFechado.FlatAppearance.BorderSize = 0;
            this.rbFechado.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.rbFechado.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Red;
            this.rbFechado.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Yellow;
            this.rbFechado.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.rbFechado.ForeColor = System.Drawing.Color.White;
            this.rbFechado.Location = new System.Drawing.Point(217, 22);
            this.rbFechado.Name = "rbFechado";
            this.rbFechado.Size = new System.Drawing.Size(84, 23);
            this.rbFechado.TabIndex = 23;
            this.rbFechado.Text = "Fechada";
            this.rbFechado.UseVisualStyleBackColor = true;
            // 
            // rbAberto
            // 
            this.rbAberto.AutoSize = true;
            this.rbAberto.FlatAppearance.BorderColor = System.Drawing.Color.White;
            this.rbAberto.FlatAppearance.BorderSize = 0;
            this.rbAberto.FlatAppearance.CheckedBackColor = System.Drawing.Color.FromArgb(((int)(((byte)(132)))), ((int)(((byte)(255)))), ((int)(((byte)(0)))));
            this.rbAberto.FlatAppearance.MouseDownBackColor = System.Drawing.Color.Red;
            this.rbAberto.FlatAppearance.MouseOverBackColor = System.Drawing.Color.Yellow;
            this.rbAberto.Font = new System.Drawing.Font("Candara", 12F, System.Drawing.FontStyle.Bold, System.Drawing.GraphicsUnit.Point);
            this.rbAberto.ForeColor = System.Drawing.Color.White;
            this.rbAberto.Location = new System.Drawing.Point(120, 22);
            this.rbAberto.Name = "rbAberto";
            this.rbAberto.Size = new System.Drawing.Size(74, 23);
            this.rbAberto.TabIndex = 22;
            this.rbAberto.Text = "Aberta";
            this.rbAberto.UseVisualStyleBackColor = true;
            // 
            // TelaPrincipal
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 15F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.BackColor = System.Drawing.Color.FromArgb(((int)(((byte)(9)))), ((int)(((byte)(14)))), ((int)(((byte)(17)))));
            this.ClientSize = new System.Drawing.Size(1062, 472);
            this.Controls.Add(this.groupBox1);
            this.Controls.Add(this.btnAtualizar);
            this.Controls.Add(this.txtDescricao);
            this.Controls.Add(this.label4);
            this.Controls.Add(this.panel2);
            this.Controls.Add(this.txtID);
            this.Controls.Add(this.label3);
            this.Controls.Add(this.btnFiltrar);
            this.Controls.Add(this.rbQualquer);
            this.Controls.Add(this.panel1);
            this.Controls.Add(this.txtTipo);
            this.Controls.Add(this.label2);
            this.Controls.Add(this.rbTelefone);
            this.Controls.Add(this.rbComentario);
            this.Controls.Add(this.label1);
            this.Controls.Add(this.txtPesquisar);
            this.Controls.Add(this.pbxLupa);
            this.Controls.Add(this.dgvDenuncias);
            this.Name = "TelaPrincipal";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterScreen;
            this.Text = "TelaPrincipal";
            this.WindowState = System.Windows.Forms.FormWindowState.Maximized;
            ((System.ComponentModel.ISupportInitialize)(this.dgvDenuncias)).EndInit();
            ((System.ComponentModel.ISupportInitialize)(this.pbxLupa)).EndInit();
            this.groupBox1.ResumeLayout(false);
            this.groupBox1.PerformLayout();
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.DataGridView dgvDenuncias;
        private System.Windows.Forms.DataGridViewTextBoxColumn clnId;
        private System.Windows.Forms.DataGridViewTextBoxColumn clnTipo;
        private System.Windows.Forms.DataGridViewTextBoxColumn clnLocal;
        private System.Windows.Forms.DataGridViewTextBoxColumn clnDescricao;
        private System.Windows.Forms.DataGridViewTextBoxColumn clnDenunciador;
        private System.Windows.Forms.DataGridViewTextBoxColumn clnDenunciado;
        private System.Windows.Forms.PictureBox pbxLupa;
        private System.Windows.Forms.TextBox txtPesquisar;
        private System.Windows.Forms.Label label1;
        private System.Windows.Forms.RadioButton rbComentario;
        private System.Windows.Forms.RadioButton rbTelefone;
        private System.Windows.Forms.Label label2;
        private System.Windows.Forms.TextBox txtTipo;
        private System.Windows.Forms.Panel panel1;
        private System.Windows.Forms.RadioButton rbQualquer;
        private System.Windows.Forms.Button btnFiltrar;
        private System.Windows.Forms.TextBox txtID;
        private System.Windows.Forms.Panel panel2;
        private System.Windows.Forms.Label label3;
        private System.Windows.Forms.TextBox txtDescricao;
        private System.Windows.Forms.Label label4;
        private System.Windows.Forms.Button btnAtualizar;
        private System.Windows.Forms.GroupBox groupBox1;
        private System.Windows.Forms.RadioButton rbQualquerStatus;
        private System.Windows.Forms.RadioButton rbFechado;
        private System.Windows.Forms.RadioButton rbAberto;
    }
}